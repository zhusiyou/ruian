<template>
  <div>
    <a-button @click="showModal"><slot>新增供应商</slot></a-button>
    <a-modal v-model:visible="visible" title="新增/修改供应商" @ok="handleOk">
      <add-customer ref="formCustomer" :editCustomer="editCustomer" />
    </a-modal>
  </div>
</template>

<script>
import AddCustomer from "@/components/AddCustomer.vue";
import { ref } from "vue";
export default {
  components: {
    AddCustomer,
  },
  props:{
    editCustomer:{
      required: false,
      type: Object,
    },
  },
  setup(props) {
    const formCustomer = ref(null)
    const visible = ref(false);

    const showModal = () => {
      visible.value = true;
    };

    const handleOk = () => {
      formCustomer.value.save()
      if(props.editCustomer){
        visible.value = false;
      }      
    };
    return {
      visible,
      showModal,
      handleOk,
      formCustomer,
    };
  },
};
</script>