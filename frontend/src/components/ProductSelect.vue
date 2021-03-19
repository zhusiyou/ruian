<template>
  <a-select
    show-search
    label-in-value
    v-model:value="productName"
    placeholder="输入商品名称"
    style="width: 200px"
    :default-active-first-option="false"
    :show-arrow="false"
    :filter-option="false"
    :not-found-content="null"
    @search="handleSearch"
    @change="handleChange"
  >
    <a-select-option v-for="d in data" :key="d.value">
      {{ d.text }}
    </a-select-option>
  </a-select>
</template>
<script>
// import jsonp from 'fetch-jsonp';
// import querystring from 'querystring';
import { defineComponent, ref, computed } from "vue";
import { select as selectProduct } from "@/api/product";
let timeout;
let currentValue = "";

function fetch(value, callback) {
  if (timeout) {
    clearTimeout(timeout);
    timeout = null;
  }

  currentValue = value;

  function fake() {
    // const str = querystring.encode({
    //   code: 'utf-8',
    //   q: value,
    // });
    selectProduct(value)
      //   .then(response => response.data)
      .then((result) => {
        if (currentValue === value) {
          const data = [];
          result.forEach((r) => {
            data.push({
              value: r.id,
              text: r.productName,
            });
          });
          callback(data);
        }
      });
  }

  timeout = setTimeout(fake, 300);
}

export default defineComponent({
  props: {
    // text: {
    //   required: false,
    //   type: String,
    // },
    product: {
      required: false,
      type: Object,
    },
  },
  emit: ["changed"],
  setup(props, { emit }) {
    const data = ref([]);
    const handleSearch = (val) => {
      fetch(val, (d) => (data.value = d));
    };

    const handleChange = (val) => {
      //   fetch(val, d => (data.value = d));
      emit("changed", val);
    };

    // const productId = computed({
    //   get: () => props.modelValue ?? "",
    //   set: (value) => {
    //     emit("update:modelValue", value);
    //   },
    // });
    // const productId = ref()
    const productName = computed(() => {
      console.log(props.product);      
      return {
        label: props.product.productName,
        value: props.product.productId
      };
    });
    return {
      handleSearch,
      data,
      // productId,
      productName,
      handleChange,
    };
  },
});
</script>