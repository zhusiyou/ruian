
<template>
  <div>
    <a-form layout="inline" :model="form">
      <a-form-item label="选择月份">
        <a-date-picker />
      </a-form-item>
      <a-form-item>
        <a-input v-model="form.fieldB" placeholder="商品名称" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary"> 查询 </a-button>
      </a-form-item>
      <a-form-item>
        <a-button> 导入盘库数据 </a-button>
      </a-form-item>
      <a-form-item>
        <a-button> 新增盘库数据 </a-button>
      </a-form-item>
    </a-form>

    <a-table :columns="columns" :data-source="dataSource" size="small" bordered>
      <template
        v-for="col in ['name', 'age', 'address']"
        #[col]="{ text, record }"
        :key="col"
      >
        <div>
          <a-input
            v-if="editableData[record.key]"
            v-model:value="editableData[record.key][col]"
            style="margin: -5px 0"
          />
          <template v-else>
            {{ text }}
          </template>
        </div>
      </template>
      <template #operation="{ record }">
        <div class="editable-row-operations">
          <span v-if="editableData[record.key]">
            <a @click="save(record.key)">Save</a>
            <a-popconfirm title="Sure to cancel?" @confirm="cancel(record.key)">
              <a>Cancel</a>
            </a-popconfirm>
          </span>
          <span v-else>
            <a @click="edit(record.key)">Edit</a>
          </span>
        </div>
      </template>
    </a-table>
  </div>
</template>
<script>
// import { DownOutlined } from '@ant-design/icons-vue';
import { cloneDeep } from "lodash-es";
import { reactive, ref } from "vue";
const form = {
  fieldA: "",
  fieldB: "",
};
const columns = [
  {
    title: "月份",
    dataIndex: "date",
    slots: { customRender: "date" },
  },
  {
    title: "编号",
    dataIndex: "productCode",
    slots: { customRender: "productCode" },
  },
  {
    title: "商品名称",
    dataIndex: "name",
    width: "35%",
    slots: { customRender: "name" },
  },
  {
    title: "单位",
    dataIndex: "unit",
    width: "15%",
    slots: { customRender: "unit" },
  },
  {
    title: "库存",
    dataIndex: "count",
    width: "20%",
    slots: { customRender: "count" },
  },
  {
    title: "操作",
    dataIndex: "operation",
    slots: { customRender: "operation" },
  },
];
let data = [];
for (let i = 0; i < 30; i++) {
  data.push({
    key: i.toString(),
    name: "畜用VC-" + (200 + i * 20).toString() + "ml",
    unit: "个",
    productCode: `no. ${i}`,
    date: "2021-06",
    count: 100,
  });
}
export default {
  setup() {
    const dataSource = ref(data);
    const editableData = reactive({});

    const edit = (key) => {
      editableData[key] = cloneDeep(
        dataSource.value.filter((item) => key === item.key)[0]
      );
    };
    const save = (key) => {
      Object.assign(
        dataSource.value.filter((item) => key === item.key)[0],
        editableData[key]
      );
      delete editableData[key];
    };
    const cancel = (key) => {
      delete editableData[key];
    };
    return {
      dataSource,
      columns,
      editingKey: "",
      editableData,
      edit,
      save,
      cancel,
      form,
    };
  },
};
</script>
<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>