<template>
  <a-select ref="select" v-model:value="value" @change="handleChange">
    <template v-for="item in categories" :key="item.id">
      <a-select-option :value="item.id">{{
        item.categoryName
      }}</a-select-option>
    </template>
  </a-select>
</template>
<script>
import { getTopLevelCategory } from "@/api/category";
import { ref, computed } from "vue";
export default {
  props: { selectedValue: String },
  emits: ["change"],
  setup(props, ctx) {
    let categories = ref([]);

    const load = () => {
      categories.value.length = 0;
      categories.value.push({ id: "", categoryName: "一级分类" });
      getTopLevelCategory().then((res) => {
        categories.value = categories.value.concat(res.data);
      });
    };
    load();

    const value = computed(() => {
      return props.selectedValue ? props.selectedValue : "";
    });

    const handleChange = (value) => {
      ctx.emit("change", value);
    };

    return {
      categories,
      value,
      handleChange,
      load,
    };
  },
};
</script>