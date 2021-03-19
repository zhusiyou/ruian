<template>
  <div>
    <a-form layout="inline" :model="inputOrder">
      <a-row>
        <a-col :span="12">
          <a-form-item label="入库单号">
            <a-input
              v-model:value="inputOrder.inputCode"
              placeholder="入库单号"
              style="width: 260px"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="经手人">
            <a-input
              v-model:value="inputOrder.storeManager"
              placeholder="经手人"
              style="width: 260px"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-item label="入库日期">
            <a-date-picker
              v-model:value="inputOrder.inputDate"
              valueFormat="YYYY-MM-DD"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="供应商">
            <a-select
              style="width: 120px"
              ref="select"
              v-model:value="inputOrder.supplierId"
            >
              <a-select-option value="">选择供应商</a-select-option>
              <template v-for="item in customers" :key="item.id">
                <a-select-option :value="item.id">{{
                  item.companyName
                }}</a-select-option>
              </template>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <a-textarea
            placeholder="备注"
            :rows="1"
            v-model:value="inputOrder.remark"
            :maxlength="500"
          />
        </a-col>
      </a-row>
    </a-form>
    <!-- <a-divider /> -->
    <a-table
      :columns="columns"
      :data-source="detailData"
      :pagination="showPage"
      size="small"
      rowKey="id"
      bordered
    >
      <template
        v-for="col in ['productName', 'count', 'price', 'amount']"
        #[col]="{ text, record }"
        :key="col"
      >
        <div>
          <product-select
            v-if="editableData[record.id] && col == 'productName'"
            :product="editableData[record.id]"
            @changed="(val) => selectChange(record.id, val)"
          />
          <a-input
            v-else-if="editableData[record.id] && col != 'productName'"
            v-model:value="editableData[record.id][col]"
            style="margin: -5px 0"
          />
          <template v-else>
            {{ text }}
          </template>
        </div>
      </template>
      <template #operation="{ record }">
        <div class="editable-row-operations">
          <span v-if="editableData[record.id]">
            <a @click="save(record.id)">保存</a>
            <a @click="cancel(record.id)">取消</a>
          </span>
          <span v-else>
            <a @click="edit(record.id)">修改</a>
            <a-popconfirm title="确定删除吗?" @confirm="remove(record.id)">
              <a>删除</a>
            </a-popconfirm>
            <!-- <a @click="remove(record.id)">删除</a> -->
          </span>
        </div>
      </template>
      <template #footer
        ><a-button @click="add" :disabled="editing"
          >增加商品</a-button
        ></template
      >
    </a-table>
    <a-form-item>
      <div class="editable-row-operations">
      <a-button type="primary" @click="submit" :disabled="editing">
        保存
      </a-button>      
      <a-button @click="()=>$router.go(-1)">返回</a-button>
      </div>
    </a-form-item>
  </div>
</template>
<script>
// import { DownOutlined } from '@ant-design/icons-vue';
import { cloneDeep } from "lodash-es";
// import moment from "moment";
import { reactive, ref, computed } from "vue";
import { getAll as getCustomers } from "@/api/customer";
import ProductSelect from "@/components/ProductSelect.vue";
import { message } from "ant-design-vue";
import { save as saveOrder, page } from "@/api/inputOrder";
import { useRoute, useRouter } from "vue-router";

const columns = [
  // {
  //   title:'id',
  //   dataIndex:'id',
  // },
  {
    title: "商品名称",
    dataIndex: "productName",
    slots: { customRender: "productName" },
  },
  // {
  //   title: "单位",
  //   dataIndex: "unit",
  //   width: "10%",
  //   slots: { customRender: "unit" },
  // },
  {
    title: "数量",
    dataIndex: "count",
    width: "10%",
    slots: { customRender: "count" },
  },
  {
    title: "单价",
    dataIndex: "price",
    width: "15%",
    slots: { customRender: "price" },
  },
  {
    title: "金额",
    dataIndex: "amount",
    width: "15%",
  },
  {
    title: "操作",
    dataIndex: "operation",
    width: "10%",
    slots: { customRender: "operation" },
  },
];
export default {
  components: { ProductSelect },
  setup() {
    const details = ref([]);
    const detailData = computed(() =>
      details.value.filter((item) => item.status != "deleted")
    );
    const editableData = reactive({});
    let tempId = 0;
    const idLength = 36;
    let editing = ref(false);

    let inputOrder = reactive({
      id: "",
      inputCode: "",
      storeManager: "",
      supplierId: "",
      inputDate: null,
      remark: "",
      version: 0,
      details: [],
    });

    const route = useRoute();
    const isUpdate = !!route.query.id;

    if (isUpdate) {
      page(route.query).then((res) => {
        Object.assign(inputOrder, res.data.data[0]);
        details.value = inputOrder.details;
      });
    }

    const edit = (key) => {
      editing.value = true;
      editableData[key] = cloneDeep(
        details.value.filter((item) => key === item.id)[0]
      );
    };
    const save = (key) => {
      if (key.length == idLength) {
        //TODO: 备份数据
        editableData[key].status = "updated";
      } else {
        editableData[key].status = "added";
      }
      if (!editableData[key].productName) {
        return message.error("请选择商品");
      }
      if (editableData[key].count == 0) {
        return message.error("请输入数量");
      }
      editing.value = false;
      editableData[key].amount =
        editableData[key].price * editableData[key].count;
      Object.assign(
        details.value.filter((item) => key === item.id)[0],
        editableData[key]
      );

      delete editableData[key];
    };
    const remove = (key) => {
      const index = details.value.indexOf(
        details.value.filter((item) => key === item.id)[0]
      );
      if (key.length == idLength) {
        details.value[index].status = "deleted";
      } else {
        details.value.splice(index, 1); //deleted array items
      }
      //delete editableData[key];
    };
    const cancel = (key) => {
      editing.value = false;
      if (key.length !== idLength) {
        const index = details.value.indexOf(
          details.value.filter((item) => key === item.id)[0]
        );
        details.value.splice(index, 1); //deleted array items
      }
      delete editableData[key];
    };
    const add = () => {
      editing.value = true;
      details.value.push({
        id: tempId,
        productId: "",
        productName: "",
        unit: "",
        count: "",
        price: "",
        amount: 0,
        status: "",
      });
      editableData[tempId] = cloneDeep(
        details.value.filter((item) => tempId === item.id)[0]
      );
      tempId++;
    };
    // const reload = inject("reload");

    const customers = ref([]);
    getCustomers().then((res) => {
      customers.value = res.data;
    });

    //行编辑，商品选择
    const selectChange = (key, val) => {
      editableData[key].productName = val.label.el.data;
      editableData[key].productId = val.key;
    };

    const router = useRouter();
    const submit = () => {
      if (!inputOrder.inputCode) {
        return message.error("请输入入库单号");
      }
      if (!details.value.length) {
        return message.error("请输入入库商品");
      }

      inputOrder.details = details.value;
      inputOrder.amount = 0;
      inputOrder.details.forEach((item) => {
        if (item.status != "deleted") {
          inputOrder.amount += item.amount;
        }
      });
      // console.log(inputOrder);
      
      saveOrder(inputOrder).then((res) => {
        if (res.data) {
          message.success("操作成功");
          router.push({ name: "input" });
        }
      });
    };

    return {
      details,
      columns,
      editingKey: "",
      editableData,
      edit,
      save,
      cancel,
      showPage: false,
      add,
      inputOrder,
      customers,
      remove,
      selectChange,
      submit,
      editing,
      detailData,
      // moment,
    };
  },
};
</script>
<style scoped>
.editable-row-operations a,button {
  margin-right: 8px;
}
</style>