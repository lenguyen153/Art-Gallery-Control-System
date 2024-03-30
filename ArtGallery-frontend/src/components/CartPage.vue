<template>
    <div id="cartpage">
        <h1> Welcome </h1>
        <h2> Artworks in Cart</h2>
        <table id="table">
            <tr>
                <td> Name </td>
                <td> Description </td>
                <td> Artstyle </td>
                <td> Price </td>
                <td> Remove from cart </td>
            </tr>
            <tr v-for="artwork in artworks" :key="artwork.name"> 
                <td> {{artwork.name}} </td>
                <td> {{artwork.description}} </td>
                <td> {{artwork.artstyle}} </td>
                <td> {{artwork.price}} </td>
                <button type="button" v-on:click="removeArtwork(artwork.name)"> Remove </button>
            </tr>
        </table>
        <p>
            <span v-if="errorCheckout" style="color:red">Error: {{errorCheckout}} </span>
        </p>
        <button type="button" v-on:click="checkout()"> Checkout </button>
        <button type="button" v-on:click="returnToCustomerPage()"> Return </button>
    </div>
</template>

<script src="./CartPageScript.js">

</script>

<style>
#table {
    width: 100%;
    border: 1px solid black;
    text-align: center;
}
    #cartpage {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        color: #2c3e50;
        background: #f2ece8;
    }
</style>