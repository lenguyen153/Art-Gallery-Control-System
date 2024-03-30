<template>
    <div id="hello">
        <h1>Welcome</h1>
        <p>Welcome to our art gallery website! Our goal is to to help artist and customer connect by 
            creating a sytem from in which potential customer can buy their desired artworks. 
            Please sign in or create an account (as a customer or an artist)
            to browse the available artworks or add your own artworks.
        </p> 
        <hr> 
        <h2> Options </h2>
        <button type="button" v-on:click="goToCustomerLoginPage()" > CustomerLogin</button>
        <button type="button" v-on:click="goToArtistLoginPage()" > ArtistLogin</button>
        <button type="button" v-on:click="goToManagerLoginPage()" > ManagerLogin</button>
        <button type="button" v-on:click="goToCreateCustomerPage()"> Create a Customer Account</button>
        <button type="button" v-on:click="goToCreateArtistPage()"> Creat an Artist Account</button>
        <button type="button" v-on:click="goToCreateManagerPage()"> Create a Manager Account</button>
        <hr>
    </div> 
</template>
<script src="./HomeScript.js">
</script>
<style>
    #hello {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        color: #2c3e50;
        background: #f2ece8;
    }
</style>