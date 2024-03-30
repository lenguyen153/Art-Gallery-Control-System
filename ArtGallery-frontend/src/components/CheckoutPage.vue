<template>
    <div id="hello">
        <h1> Checkout </h1>
        <h2> Succesful </h2>
        <p>
            Congratulation, you have succesfully bought an artwork!!!!
        </p> 
        <p>
            Please return to your customer page or exit
        </p>
        <hr> 
        <h2> Options </h2>
        <button type="button" v-on:click="returnToCustomerPage()" > return </button>
        <button type="button" v-on:click="exit()" > Exit </button>
        <hr>
    </div> 
</template>
<script src="./CheckoutPageScript.js">
</script>
<style>
    #hello {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        color: #2c3e50;
        background: #f2ece8;
    }
</style>