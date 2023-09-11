<template>
  <div>
    <ul>
        <li v-if="user.id != 'admin'">
            id : {{ user.id }} , 이름 : {{ user.name }}, 이메일 : {{ user.email }}
            <b-btn @click="removeUser(user.id)">삭제</b-btn>
        </li>
    </ul>
  </div>
</template>

<script>
export default {
  name : "AdminUserListItem",
  props : {
    user : Object
  },
  methods :{
    removeUser(id){
      this.$emit("removeUser", id)
    }
  }
}
</script>

<style>

</style>