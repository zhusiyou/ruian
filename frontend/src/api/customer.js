import service from '@/api/request.js'

export function getAll(){
    return service.get('/customer/all')
}

export function save(custom){
    return service.post('/customer/save', custom)
}