import service from '@/api/request.js'

export function getTopLevelCategory(){
    return service.get('/category/level/0')
}

export function getAll(){
    return service.get('/category/all')
}

export function save(category){
    return service.post('/category/save', category)
}