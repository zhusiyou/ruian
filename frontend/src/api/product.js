import service from '@/api/request.js'

export function save(product){
    return service.post('/product/save', product)
}

export function page(queryParam){
    const query = {
        name: '',
        pageIndex: 1,
        pageSize: 2
    }
    Object.assign(query, queryParam)
    return service.get(`/product/page?name=${query.name}&pageIndex=${query.pageIndex}&pageSize=${query.pageSize}`)
}

export function select(txt){
    return page({
        name: txt,
        pageIndex: 1,
        pageSize: 20
    }).then(res=>{
        return new Promise((resolve, reject)=>{
            resolve(res.data.data)
            reject(res)
        })
    })

    
}

