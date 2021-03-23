
<template>
  <div>
    <a-form layout="inline" :model="form">
      <a-form-item label="选择月份">
        <a-month-picker v-model:value="form.date" value-format="yyyy-MM" />
      </a-form-item>
      <a-form-item>
        <a-input v-model="form.productName" placeholder="商品名称" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="search"> 查询 </a-button>
      </a-form-item>
      <a-form-item>
        <a-upload name="file" action="http://localhost:8181/taking/upload">
          <a-button>
            上传盘库数据
          </a-button>
        </a-upload>
      </a-form-item>
      <a-form-item>
        <a-button> 新增盘库数据 </a-button>
      </a-form-item>
    </a-form>

    <a-table :columns="columns" :data-source="dataSource" size="small" bordered>
    </a-table>
  </div>
</template>
<script>
// import { DownOutlined } from '@ant-design/icons-vue';
// import { cloneDeep } from "lodash-es";
import { reactive, ref } from "vue";
const form  = reactive({
  date: null,
  productName: "",
});
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
    const search = ()=>{
      console.log(JSON.stringify(form))
    }
    return {
      dataSource,
      columns,
      form,
      search,
    };
  },
};
</script>
<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>