import axios from 'axios'

const service = axios.create({
    baseURL: 'http://localhost:8181',
    timeout: 30000
})

export default service