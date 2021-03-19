<template>
    <div>应付款<category-group @change="handleChange"></category-group></div>
</template>
<script>
import CategoryGroup from '@/components/CategoryGroup'
export default {
    components:{
        CategoryGroup
    },
    setup(){
        const handleChange = (value) =>{
            console.log(value)
        }
        return {
            handleChange
        }
    } 
}
</script>