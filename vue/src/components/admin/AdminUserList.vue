<template>
  <div>
    list 리스트라고 리스트
    <admin-user-list-item
    v-for="user in users" :key="user.id"
    :user="user"
    @removeUser="removeUser">
    </admin-user-list-item>
  </div>
</template>

<script>
import AdminUserListItem from "./AdminUserListItem.vue";
import apiInstance from "@/api/http";
const http = apiInstance();
export default {
    name : "AdminUserList",
    components : {AdminUserListItem},
    data(){
        return{
            users : [],
        }
    },
    created(){
        this.getUserList();
    },
    methods :{
        getUserList(){
            http.get("/admin/lalaququpurin")
            .then(({data})=>{
                console.log(data);
                this.users = data
            })
        },
        removeUser(id){
            http.delete("/admin/lalaququpurin/"+id)
            .then(({data})=>{
                console.log(data);
                if(data)
                    this.getUserList();
            })
        }
    }
}
</script>

<style>

</style>