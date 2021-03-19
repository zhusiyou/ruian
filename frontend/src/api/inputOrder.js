import service from '@/api/request.js'

export function save(order){
    return service.post('/inputOrder/save', order);
}

export function page(queryParam){
    const query = {
        id: '',
        inputCode:'',
        pageIndex: 1,
        pageSize: 10
    }
    Object.assign(query, queryParam)
    if(query.id){
        query.pageSize = 1
    }
    
    return service.get(`/inputOrder/page?code=${query.inputCode}&id=${query.id}&pageIndex=${query.pageIndex}&pageSize=${query.pageSize}`)
}