<template>
  <div>
    <a-form :labelCol="labelCol" :model="customer">
      <a-form-item label="供应商名称(*)">
        <a-input v-model:value="customer.companyName" style="width: 260px" />
      </a-form-item>
      <a-form-item label="联系人">
        <a-input v-model:value="customer.linkman" />
      </a-form-item>
      <a-form-item label="联系电话">
        <a-input v-model:value="customer.telephone" />
      </a-form-item>
      <a-form-item label="地址">
        <a-input style="width: 300px" v-model:value="customer.address" />
      </a-form-item>
      <a-form-item label="备注">
        <a-textarea
          :rows="2"
          style="width: 300px"
          v-model:value="customer.remark"
        />
      </a-form-item>
      <!-- <a-form-item :wrapper-col="{ span: 14, offset: 6 }"><a-button type="primary">保存</a-button></a-form-item> -->
    </a-form>
  </div>
</template>
<script>
import { reactive } from "vue";
import { message } from "ant-design-vue";
import { save as saveCustomer } from "@/api/customer";
export default {
  props: {
    editCustomer: {
      type: Object,
      required: false,
    },
  },
  setup(props) {
    const newCustomer = {
      id: "",
      companyName: "",
      linkman: "",
      telephone: "",
      address: "",
      remark: "",
    };

    const customer = reactive(props.editCustomer || newCustomer);

    const save = () => {
      if (!customer.companyName) {
        return;
      }
      saveCustomer(customer).then((res) => {
        if (res.data) {
          if (!customer.id) {            
            customer.companyName = "";
            customer.linkman = "";
            customer.telephone = "";
            customer.address = "";
            customer.remark = "";
          }
          message.success("操作成功");
        }else{
          message.error("操作失败")
        }
      });
    };
    return {
      labelCol: { span: 6 },
      customer,
      save,
    };
  },
};
</script>
<style scoped>
.ant-form-item {
  margin-bottom: 6px;
}
</style>