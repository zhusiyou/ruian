import { createRouter, createWebHashHistory} from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'ruian',
        component: ()=> import('@/views/Layout.vue'),
        children: [
            {
                path: 'category',
                name: 'Category',
                component: ()=> import('../views/product/Category.vue')
            },
            {
                path: 'product',
                name: 'Product',
                component: ()=> import('../views/product/Product.vue')
            },
            {
                path: 'customer',
                name: 'customer',
                component: ()=> import('../views/customer/Customer.vue')
            },
            {
                path: 'stock',
                name: 'stock',
                component: ()=> import('../views/stock/Stock.vue')
            },
            {
                path: 'input',
                name: 'input',
                component: ()=> import('../views/stock/Input.vue')
            },
            {
                path: 'editInput',
                name: 'editInput',
                component: ()=> import('../views/stock/EditInput.vue')
            },
            {
                path: 'taking',
                name: 'taking',
                component: ()=> import('../views/stock/Taking.vue')
            },
            {
                path: 'income',
                name: 'income',
                component: ()=> import('../views/report/Income.vue')
            },
            {
                path: 'cost',
                name: 'cost',
                component: ()=> import('../views/report/Cost.vue')
            }
        ]
    },    
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router