import {exportExcel} from '@/api/excel.js'

export function exportStock(){
    return exportExcel({
        url: '/product/export',
        fileName: '库存数据.xlsx',
    })
}