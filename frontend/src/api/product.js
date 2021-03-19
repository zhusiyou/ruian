import service from '@/api/request.js'

export function save(product){
    return service.post('/product/save', product)
}

export function page(productQuery){
    // console.log(productQuery)
    return service.get(`/product/page?name=${productQuery.name}&pageIndex=${productQuery.pageIndex}&pageSize=${productQuery.pageSize}`)
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

