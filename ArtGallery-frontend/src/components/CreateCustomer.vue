<template>
    <div id="Login">
        <h2> Customer Sign Up </h2>
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
                <td>Address</td>
                <td>
                    <input type="text" v-model="Address" placeholder="Address">
                </td>
            </tr>
            <tr>
                <td>
                    <button type="button" v-on:click="createCustomer(Name, Username, Password, Address)"> Create </button>
                </td>
                <td>
                    <button type="button" v-on:click="returnToHome()"> Return to home</button>
                </td>
            </tr>
        </table>
        <p>
            <span v-if="error" style="color:red"> Error: {{error}} </span>
        </p>
        <p>
            <span v-if="createdMsg" style="color:black"> {{createdMsg}} </span>
        </p>
    </div>
</template>
<script src="./CreateCustomerScript.js">
</script>
<style>
    #Login {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        color: #2c3e50;
        background: #f2ece8;
    }
</style>