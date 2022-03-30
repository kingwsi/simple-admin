import request from '@/utils/request'

const Api = {
  // get my info
  Page: '/api/${controllerMappingHyphen}/page',
  Add: '/api/${controllerMappingHyphen}',
  Update: '/api/${controllerMappingHyphen}',
  Delete: '/api/${controllerMappingHyphen}'
}

export function GetPage (parameter) {
  return request({
    url: Api.Page,
    method: 'get',
    params: parameter
  })
}

export function Create (data) {
  return request({
    url: Api.Add,
    method: 'post',
    data: data
  })
}

export function UpdateById (data) {
  return request({
    url: Api.Update,
    method: 'put',
    data: data
  })
}

export function DeleteById (id) {
  return request({
<#noparse>
    url: `${Api.Delete}/${id}`,
    method: 'delete'
</#noparse>
  })
}
