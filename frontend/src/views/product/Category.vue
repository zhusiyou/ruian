<template>
  <div>
    <a-form layout="inline" :model="form">
      <a-form-item label="上级分类">
        <top-level-category @change="handleChange" ref="categorySelect" />
      </a-form-item>
      <a-form-item>
        <a-input v-model:value="form.categoryName" placeholder="分类名称" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="add"> 保存 </a-button>
      </a-form-item>
    </a-form>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      rowKey="id"
      size="small"
      bordered
    >
      <template
        v-for="col in ['categoryName', 'level', 'parentId']"
        #[col]="{ text, record }"
        :key="col"
      >
        <div>
          <a-input
            v-if="editableData[record.id] && col != 'parentId'"
            v-model:value="editableData[record.id][col]"
            style="margin: -5px 0"
          />
          <top-level-category
            v-else-if="editableData[record.id] && col == 'parentId'"
            :selectedValue="editableData[record.id][col]"
            @change="(value) => handleEditChange(value, record.id)"
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
import TopLevelCategory from "../../components/TopLevelCategory.vue";
import { getAll, save as saveCategory } from "@/api/category";
import {message} from 'ant-design-vue'

const form = reactive({
  id: "",
  categoryName: "",
  level: 0,
  parentId: "",
});
const columns = [
  {
    title: "分类名称",
    dataIndex: "categoryName",
    slots: { customRender: "categoryName" },
  },
  {
    title: "层级",
    dataIndex: "level",
    // slots: { customRender: "level" },
  },
  {
    title: "父类名称",
    dataIndex: "parentName",
    slots: { customRender: "parentId" },
  },
  {
    title: "操作",
    dataIndex: "operation",
    slots: { customRender: "operation" },
  },
];

export default {
  components: {
    TopLevelCategory,
  },
  setup() {
    let dataSource = ref([]);
    let editing = false;
    const categorySelect = ref(null)
    const editableData = reactive({});

    const edit = (key) => {
      if (editing) {
        return;
      }
      editing = true;
      editableData[key] = cloneDeep(
        dataSource.value.filter((item) => key === item.id)[0]
      );
    };
    const save = (key) => {
      saveCategory(editableData[key]).then(() => {
        Object.assign(
          dataSource.value.filter((item) => key === item.id)[0],
          editableData[key]
        );

        delete editableData[key];
        editing = false;
        load()
        categorySelect.value.load()
      });
    };
    const cancel = (key) => {
      delete editableData[key];
      editing = false;
    };

    const load = () => {
      getAll().then((res) => {
        dataSource.value = res.data;
      });
    };
    load();

    const handleChange = (value) => {
      form.parentId = value;
      form.level = form.parentId ? 1 : 0;
    };

    const handleEditChange = (value, key) => {
      let obj = editableData[key];
      if (value != obj.id) {
        obj.parentId = value;
      }
      obj.level = value ? 1 : 0;
    };

    const add = function() {
      if (!form.categoryName) {
        return;
      }
      saveCategory(form).then((res) => {
        if (res.data) {
          form.categoryName = "";
          // form.parentId = "";
          form.id = "";
          // form.level = fir;

          load()
          categorySelect.value.load()
          message.success('保存成功')
        }
      });
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
      handleChange,
      handleEditChange,
      add,
      categorySelect,
    };
  },
};
</script>

<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>