import {exportExcel} from '@/api/excel.js'
import service from '@/api/request.js'

export function exportStock(){
    return exportExcel({
        url: '/stock/export',
        fileName: '库存数据.xlsx',
    })
}

export function page(queryParam){
    const query = {
        productName: '',
        pageIndex: 1,
        pageSize: 2
    }
    Object.assign(query, queryParam)
    return service.get(`/stock/page?productName=${query.productName}&pageIndex=${query.pageIndex}&pageSize=${query.pageSize}`)
}

export function reset(queryParam){
    return service.post("/stock/reset?date=" + queryParam.date)
}

export function cost(queryParam){
    return service.get("/stock/cost?date=" + queryParam.date)
}