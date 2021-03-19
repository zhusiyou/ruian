<template>
  <div>
    <a-button @click="link('editInput')">新增入库</a-button>
    <a-table
      :columns="columns"
      :data-source="data"
      rowKey="id"
      size="small"
      class="components-table-demo-nested"
    >
      <template #operation="{ record }">
        <router-link :to="`/editInput?id=${record.id}`">修改</router-link>
      </template>
      <template #expandedRowRender="{ record }">
        <a-table
          :columns="innerColumns"
          :data-source="record.details"
          size="small"
          rowKey="id"
          :pagination="false"
          bordered
        >
        </a-table>
      </template>
    </a-table>
  </div>
</template>
<script>
import { defineComponent, ref, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import { page } from "@/api/inputOrder";
const columns = [
  {
    title: "入库单号",
    dataIndex: "inputCode",
    key: "inputCode",
  },
  {
    title: "供应商",
    dataIndex: "companyName",
    key: "companyName",
  },
  {
    title: "经办人",
    dataIndex: "inputUserId",
    key: "inputUserId",
  },
  {
    title: "入库日期",
    dataIndex: "inputDate",
    key: "inputDate",
  },
  {
    title: "金额",
    dataIndex: "amount",
    key: "amount",
  },
  {
    title: "操作",
    key: "operation",
    slots: {
      customRender: "operation",
    },
  },
];
const innerColumns = [
  {
    title: "商品名称",
    dataIndex: "productName",
    key: "productName",
  },
  {
    title: "单价",
    dataIndex: "price",
    key: "price",
  },
  {
    title: "数量",
    dataIndex: "count",
    key: "count",
    slots: {
      customRender: "count",
    },
  },
  {
    title: "小计",
    dataIndex: "amount",
    key: "amount",
    slots: {
      customRender: "amount",
    },
  },
];

export default defineComponent({
  setup() {
    const router = useRouter();
    function link(routeName) {
      router.push({ name: routeName });
    }
    // const reload = inject('reload')
    const data = ref([]);
    onBeforeMount(() => {
      page({ inputCode: "", pageIndex: "1", pageSize: "10" }).then((res) => {
        data.value = res.data.data;
      });
    });

    return {
      data,
      columns,
      innerColumns,
      link,
    };
  },
});
</script>