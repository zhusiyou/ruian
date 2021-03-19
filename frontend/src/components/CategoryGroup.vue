<template>
  <a-select v-model:value="value" @change="handleChange" style="min-width:100px">
    <template v-for="item in topCategories" :key="item.id">
      <a-select-opt-group :label="item.categoryName">
        <template v-for="c in getChildren(item.id)" :key="c.id">
          <a-select-option :value="c.id">{{ c.categoryName }}</a-select-option>
        </template>
      </a-select-opt-group>
    </template>
  </a-select>
</template>
<script>
import { getAll } from "@/api/category";
import { ref, computed } from "vue";
export default {
  props: { selectedValue: String },
  emits: ["change"],
  setup(props, ctx) {
    let categories = ref([]);
    let topCategories = computed(() =>
      categories.value.filter((item) => item.level == 0)
    );
    const getChildren = computed(() => {
      return (id) => {
        return categories.value.filter((item) => item.parentId == id);
      };
    });

    getAll().then((res) => {
        categories.value = categories.value.concat(res.data);
      });

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
      topCategories,
      getChildren,
    };
  },
};
</script>