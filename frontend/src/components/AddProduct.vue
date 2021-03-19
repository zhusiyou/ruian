<template>
  <div>
    <a-form :labelCol="labelCol" :model="product">
      <a-form-item label="商品分类">
        <category-group
          v-model:selectedValue="product.categoryId"
          @change="handleChange"
        ></category-group>
      </a-form-item>
      <a-form-item label="商品名称(*)">
        <a-input style="width: 260px" v-model:value="product.productName" />
      </a-form-item>
      <a-form-item label="单位(*)">
        <a-input v-model:value="product.unit" />
      </a-form-item>
      <a-form-item label="默认单价">
        <a-input v-model:value="product.defaultPrice" />
      </a-form-item>
      <a-form-item label="备注">
        <a-textarea
          :rows="2"
          style="width: 300px"
          v-model:value="product.remark"
        />
      </a-form-item>
      <!-- <a-form-item :wrapper-col="{ span: 14, offset: 4 }"
        ><a-button type="primary" @click="save">保存</a-button></a-form-item
      > -->
    </a-form>
  </div>
</template>
<script>
import CategoryGroup from "@/components/CategoryGroup";
import { save as saveProduct } from "@/api/product";
import { reactive } from "vue";
import {message} from 'ant-design-vue'
export default {
  components: {
    CategoryGroup,
  },
  props: {
    editProduct: {
      type: Object,
      required: false,
    },
  },
  setup(props) {
    const newProduct = () => {
      return {
        id: "",
        categoryId: "",
        productName: "",
        unit: "",
        defaultPrice: 0,
        remark: "",
      };
    };
    let product = reactive(props.editProduct || newProduct());

    let currentCategoryId = "";

    const handleChange = (value) => {
      product.categoryId = value;
      currentCategoryId = value;
    };

    const save = () => {
      if (!product.productName) {
        return;
      }
      saveProduct(product).then((res) => {
        if (res.data) {
          if (!product.id) {
            //add new
            product.id = "";
            product.productName = "";
            product.unit = "";
            product.defaultPrice = 0;
            product.remark = "";
            product.categoryId = currentCategoryId;
          }   
          message.success('操作成功')       
        }else{
          message.error('操作失败')
        }
      });
    };

    return {
      labelCol: { span: 4 },
      handleChange,
      product,
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