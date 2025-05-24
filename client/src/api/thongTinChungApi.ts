import axios from 'axios';
const API = 'http://localhost:8080/api/thong-tin-chung';

export const getAll = () => axios.get(API);
export const getById = (id: number) => axios.get(`${API}/${id}`);
export const create = (data: any) => axios.post(API, data);
export const update = (id: number, data: any) => axios.put(`${API}/${id}`, data);
export const remove = (id: number) => axios.delete(`${API}/${id}`);