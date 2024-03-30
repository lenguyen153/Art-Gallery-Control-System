<template>
  <div id="Login">
    <h2>Manager Login</h2>
    <table>
      <tr>
        <td>Username</td>
        <td>
          <input type="text" v-model="Username" placeholder="Username" />
        </td>
      </tr>
      <tr>
        <td>Password</td>
        <td>
          <input type="password" v-model="Password" placeholder="Password" />
        </td>
      </tr>
      <tr>
        <td>
          <button type="button" v-on:click="login(Username, Password)">
            Login
          </button>
        </td>
        <td>
          <button type="button" v-on:click="returnToHome()">
            Return to home
          </button>
        </td>
      </tr>
    </table>
    <p>
            <span v-if="error" style="color:red">Error: {{error}} </span>
        </p>
  </div>
</template>
<script src="./ManagerLoginScript.js">
</script>
<style>
#Login {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>