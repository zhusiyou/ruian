<template>
  <div>
    <a-form layout="inline" :model="form">
      <a-form-item>
        <a-input
          v-model:value="form.productName"
          placeholder="商品名称"
          style="width: 260px"
        />
      </a-form-item>
      <a-form-item><a-button type="primary" @click="search">查询</a-button></a-form-item>
      <a-form-item><add-product-btn></add-product-btn></a-form-item>
      <a-form-item>
        <a-upload
          name="file"
          action="http://localhost:8181/product/upload"
        >
          <a-button>
            <!-- <upload-outlined></upload-outlined> -->
            导入商品
          </a-button>
        </a-upload>
      </a-form-item>
    </a-form>
    <!-- <a-divider /> -->
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
      <template #operation="{ record }">
        <add-product-btn :editProduct="record">修改</add-product-btn>
      </template>
    </a-table>
  </div>
</template>
<script>
import AddProductBtn from "@/components/AddProductBtn.vue";
import { page } from "@/api/product";
import { computed, reactive } from "vue";
import { usePagination } from "vue-request";

const columns = [
  {
    title: "一级分类",
    dataIndex: "firstCategoryName",
    width: "15%",
  },
  {
    title: "商品分类",
    dataIndex: "categoryName",
    width: "15%",
    slots: { customRender: "categoryName" },
  },
  {
    title: "商品名称",
    dataIndex: "productName",
    width: "30%",
    slots: { customRender: "productName" },
  },
  {
    title: "单位",
    dataIndex: "unit",
    width: "15%",
    slots: { customRender: "unit" },
  },
  {
    title: "默认单价",
    dataIndex: "defaultPrice",
    slots: { customRender: "defaultPrice" },
  },
  {
    title: "操作",
    dataIndex: "operation",
    width: "10%",
    slots: { customRender: "operation" },
  },
];
export default {
  components: {
    AddProductBtn,
  },
  setup() {
    // const dataSource = ref([]);
    const form = reactive({
      productName: "",
    });

    const defaultPageSize = 10;
    // const handleUpload = ()=>{}
    const {
      data,
      run,
      loading,
      current,
      pageSize,
      total,
    } = usePagination(page, {
      formatResult: (res) => res.data,
      pagination: {
        currentKey: "pageIndex",
        pageSizeKey: "pageSize",
        // totalKey: "total",
      },
      // defaultParams: [{ name: form.productName, pageIndex: 1, pageSize: defaultPageSize }],
      // onSuccess: (data) => (ds.value = data.data),
    });
    const dataSource = computed(()=>{
     if(data.value){
       return data.value.data;
     }
     return []
   })
    const pagination = computed(() => ({
      total: total.value,
      current: current.value,
      pageSize: pageSize.value,
    }));

    const search = ()=>run({name: form.productName,
        pageIndex: 1,
        pageSize: defaultPageSize})

    const handleTableChange = function (pag) {
      run({
        name: form.productName,
        pageIndex: pag?.current,
        pageSize: pag.pageSize,
      });
    };

    return {
      dataSource,
      columns,
      form,
      handleTableChange,
      pagination,
      loading,
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