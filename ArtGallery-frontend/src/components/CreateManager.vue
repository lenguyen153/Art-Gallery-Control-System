<template>
    <div id="Login">
        <h2> Create Manager Account </h2>
        <table>
            <tr>
                <td>Name </td>
                <td>
                    <input type="text" v-model="Name" placeholder="Name">
                </td>
            </tr>
            <tr>
                <td>Username</td>
                <td>
                    <input type="text" v-model="Username" placeholder="Username">
                </td>
            </tr>
            <tr>
                <td>Password</td>
                <td>
                    <input type="text" v-model="Password" placeholder="Password">
                </td>
            </tr>
            <tr>
                <td>
                    <button type="button" v-on:click="createManager(Name, Username, Password)"> Create </button>
                </td>
                <td>
                    <button type="button" v-on:click="returnToHome()"> Return to home</button>
                </td>
            </tr>
        </table>
        <p>
            <span v-if="error" style="color:red"> Error: {{errorManager}} </span>
        </p>
        <p>
            <span v-if="createMsg" style="color:black"> {{createdMsg}} </span>
        </p>
    </div>
</template>
<script src="./CreateManagerScript.js">
</script>
<style>
    #Login {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        color: #2c3e50;
        background: #f2ece8;
    }
</style>