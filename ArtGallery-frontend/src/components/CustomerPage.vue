<template>
    <div id="customerpage">
        <h1> Welcome </h1>
        <h2>View Artworks</h2>
        <label> Art Style:
            <select v-model="selectedArtStyle">
                <option> ALL </option> 
                <option> ABSTRACT </option> 
                <option> PHOTOREALISM </option> 
                <option> IMPRESSIONISM </option> 
                <option> SURREALISM </option> 
                <option> POP </option> 
                <option> ART_NOUVEAU </option> 
                <option> MODERNISM </option> 
                <option> SCULPTURE </option> 
                <option> MODELING </option>     
            </select>
        </label>
        <button type="button" v-on:click="viewArtworksByStyle(selectedArtStyle)"> View Artworks</button>
        <!--p> {{cArtwork}} </p-->
            <!-- <ul>
    <li v-for="artwork in artworks" >
      {{ artwork }}
    </li>
    </ul> -->
        <table id="table">
            <tr>
                <td> Name </td>
                <td> Description </td>
                <td> Artist </td>
                <td> Artstyle </td>
                <td> Number of copy </td>
                <td> Price </td>
                <td> Add to cart </td>
            </tr>
            <tr v-for="artwork in artworks" :key="artwork.name"> 
                <td> {{artwork.name}} </td>
                <td> {{artwork.description}} </td>
                <td> {{artwork.artistUsername}} </td>
                <td> {{artwork.artStyle}} </td>
                <td> {{artwork.nbCopy}} </td>
                <td> {{artwork.price}} </td>
                <button type="button" v-on:click="addToCart(artwork.name)"> Add to cart </button>
            </tr>
        </table>
        <button type="button" v-on:click="viewCart()"> Check cart </button>
        <h2> View Transactions </h2>
        <button type="button" v-on:click="viewTransactions()"> View Transactions</button>
        <table id="table">
            <tr>
                <td> Price </td>
                <td> Commission </td>
                <td> Tax </td>
                <td> Date </td>
            </tr>
            <tr v-for="transaction in transactions"> 
                <td> {{transaction.price}} </td>
                <td> {{transaction.commissionApplied}} </td>
                <td> {{transaction.taxApplied}} </td>
                <td> {{transaction.date}} </td>
            </tr>
        </table>
        <h2> Update Account </h2>
        <table id="table">
            <tr>
                <td> Name </td>
                <td> Username </td>
                <td> Password </td>
                <td> Address </td>
                <td> Update </td>
            </tr>
            <tr>
                <td>
                    <input type="text" v-model="Name" placeholder="Name"> 
                </td>
                <td>
                    <input type="text" v-model="Username" placeholder="Username">
                </td>
                <td>
                    <input type="text" v-model="Password" placeholder="Password">
                </td>
                <td>
                    <input type="text" v-model="Address" placeholder="Address">
                </td>
                <td>
                    <button type="button" v-on:click="updateCustomer(Name, Username, Password, Address)"> Update </button>
                </td>
            </tr>
        </table>
        <p>
            <span v-if="errorUpdateAccount" style="color:red">Error: {{errorUpdateAccount}} </span>
        </p>
        <button type="button" v-on:click="logout()"> Logout </button>
    </div>    
</template>

<script src="./CustomerPageScript.js">

</script>

<style>
#table {
    width: 100%;
    border: 1px solid black;
    text-align: center;
}
    #customerpage {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        color: #2c3e50;
        background: #f2ece8;
    }
</style>