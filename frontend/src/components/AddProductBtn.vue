<template>
  <div>
    <a-button @click="showModal"><slot>新增商品</slot></a-button>
    <a-modal v-model:visible="visible" title="新增/修改商品" @ok="handleOk">
      <add-product ref="formProduct" :editProduct="editProduct" />
    </a-modal>
  </div>
</template>

<script>
import AddProduct from "@/components/AddProduct.vue";
import { ref } from "vue";
export default {
  components: {
    AddProduct,
  },
  props: {
    editProduct: {
      required: false,
      type: Object,
    },
  },
  setup(props) {
    const formProduct = ref(null);
    const visible = ref(false);

    const showModal = () => {
      visible.value = true;
    };

    const handleOk = () => {
      //TODO: 返回promise 通知父组件
      formProduct.value.save();
      if (props.editProduct) {
        visible.value = false;
      }
    };
    return {
      visible,
      showModal,
      handleOk,
      formProduct,
    };
  },
};
</script>