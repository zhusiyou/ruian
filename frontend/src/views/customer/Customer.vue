<template>
  <div>
    <a-form layout="inline" >
      <!-- <a-form-item>
        <a-input v-model="form.fieldB" placeholder="供应商名称" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary"> 查询 </a-button>
      </a-form-item> -->
      <a-form-item>
        <add-customer-btn />
      </a-form-item>
    </a-form>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      rowKey="id"
      size="small"
      bordered
    >
      <template #operation="{ record }">
        <add-customer-btn :editCustomer="record">修改</add-customer-btn>
      </template>
    </a-table>
  </div>
</template>
<script>
import { ref } from "vue";
import AddCustomerBtn from "@/components/AddCustomerBtn.vue";
import { getAll } from "@/api/customer";
// const form = {
//   fieldA: "",
//   fieldB: "",
// };
const columns = [
  {
    title: "供应商名称",
    dataIndex: "companyName",
    width: "25%",
  },
  {
    title: "联系人",
    dataIndex: "linkman",
    width: "10%",
  },
  {
    title: "电话",
    dataIndex: "telephone",
    width: "15%",
  },
  {
    title: "地址",
    dataIndex: "address",
  },
  {
    title: "操作",
    dataIndex: "operation",
    slots: { customRender: "operation" },
  },
];

export default {
  components: { AddCustomerBtn },
  setup() {
    const dataSource = ref([]);
    getAll().then((res) => {
      dataSource.value = res.data;
    });
    return {
      dataSource,
      columns,
      // form,
    };
  },
};
</script>
<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>