<template>
  <div>
    <a-form layout="inline" :model="form">
      <a-form-item>
        <a-input v-model:value="form.productName" placeholder="商品名称" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="search"> 查询 </a-button>
      </a-form-item>
      <a-form-item>
        <a-button @click="exportStock">导出库存</a-button>
      </a-form-item>
      <a-form-item>
        <a-upload name="file" action="http://localhost:8181/stock/upload">
          <a-button>
            <!-- <upload-outlined></upload-outlined> -->
            初始化库存数据
          </a-button>
        </a-upload>
      </a-form-item>
    </a-form>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :pagination="pagination"
      @change="handleTableChange"
      :loading="loading"
      rowKey="id"
      size="small"
      bordered
    >
    </a-table>
  </div>
</template>
<script>
import { reactive, computed, defineComponent  } from "vue";
import { exportStock, page } from "@/api/stock";
import { usePagination } from "vue-request";
const form = reactive({
  productName: "",
});
const columns = [
  {
    title: "商品名称",
    dataIndex: "productName",
    width: "25%",
    slots: { customRender: "productName" },
  },
  {
    title: "单位",
    dataIndex: "unit",
    slots: { customRender: "unit" },
  },
  {
    title: "均价",
    dataIndex: "inputPrice",
    slots: { customRender: "inputPrice" },
  },
  {
    title: "库存",
    dataIndex: "count",
    width: "20%",
    slots: { customRender: "count" },
  },
  // {
  //   title: "操作",
  //   dataIndex: "operation",
  //   slots: { customRender: "operation" },
  // },
];
export default defineComponent({
  setup() {

    const { data, run, loading, current, pageSize, total, changePageSize } = usePagination(page, {
      formatResult: (res) => res.data,
      pagination: {
        currentKey: "pageIndex",
        pageSizeKey: "pageSize",
        totalKey: "total",
      },
      defaultParams: [form],
    });
    changePageSize(2)

   const dataSource = computed(()=>{
     if(data.value){
       return data.value.data;
     }
     return []
   })

    const pagination = computed(() => {
      return {
        total: total.value,
        current: current.value,
        pageSize: pageSize.value,
      };
    });

    const search = () => run(form);

    const handleTableChange = function (pag) {
      run({
        productName: form.productName,
        pageIndex: pag?.current,
        pageSize: pag.pageSize,
      });
    };



    return {
      dataSource,
      columns,
      form,
      exportStock,
      pagination,
      loading,
      search,
      handleTableChange,
    };
  },
});
</script>
<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>