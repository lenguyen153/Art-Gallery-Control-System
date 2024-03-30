<template>
  <div id="managerpage">
    <h1>Welcome {{ username }}</h1>
    <h2>View Artworks</h2>
    <ul>
    </ul>
    <button type="button" v-on:click="viewArtworks()">View Artworks</button>
    <table id="table">
      <tr>
        <td>Name</td>
        <td>Description</td>
        <td>Artstyle</td>
        <td>Number of copy</td>
        <td>Price</td>
        <td>Delete</td>
      </tr>
      <tr v-for="artwork in artworks" :key="artwork.name">
        <td>{{ artwork.name }}</td>
        <td>{{ artwork.description }}</td>
        <td>{{ artwork.artStyle }}</td>
        <td>{{ artwork.nbCopy }}</td>
        <td>{{ artwork.price }}</td>
        <button type="button" v-on:click="deleteArtwork(artwork.name)">
          Delete
        </button>
      </tr>
    </table>
    <h2>Change Commission</h2>
    <table id="table">
      <tr>
        <td>Artists</td>
        <td>New commission value</td>
        <td>Change commission</td>
      </tr>
      <tr>
        <td>
          <select v-model="selectedArtist">
            <option disabled value="">Please select one</option>
            <option v-for="artist in artists" :key="artist.username">
              {{ artist.username }}
            </option>
          </select>
        </td>
        <td>
          <input
            type="text"
            v-model="Commission"
            placeholder="New commission"
          />
        </td>
        <button
          type="button"
          v-on:click="changeCommission(artist.username, Commission)"
        >
          Change
        </button>
      </tr>
    </table>
    <h2>View Artists</h2>
    <button type="button" v-on:click="viewArtists()">View Artists</button>
    <table id="table">
      <tr>
        <td>Name</td>
        <td>Username</td>
        <td>Ban</td>
        <td>Unban</td>
      </tr>
      <tr v-for="artist in artists">
        <td>{{ artist.name }}</td>
        <td>{{ artist.username }}</td>
        <td>{{ artist.isBanned }}</td>
        <button type="button" v-on:click="ban(artist.username)">Ban</button>
        <button type="button" v-on:click="unban(artist.username)">Unban</button>
      </tr>
    </table>
    <h2>View Customers</h2>
    <button type="button" v-on:click="viewCustomers()">View Customers</button>
    <table id="table">
      <tr>
        <td>Name</td>
        <td>Username</td>
        <td>Ban</td>
        <td>Unban</td>
      </tr>
      <tr v-for="customer in customers" :key="customer.username">
        <td>{{ customer.name }}</td>
        <td>{{ customer.username }}</td>
        <td>{{ customer.isBanned }}</td>
        <button type="button" v-on:click="ban(customer.username)">Ban</button>
        <button type="button" v-on:click="unban(customer.username)">
          Unban
        </button>
      </tr>
    </table>
    <h2>Update Account</h2>
    <table id="table">
      <tr>
        <td>Name</td>
        <td>Username</td>
        <td>Password</td>
        <td>Update</td>
      </tr>
      <tr>
        <td>
          <input type="text" v-model="Name" placeholder="Name" />
        </td>
        <td>
          <input type="text" v-model="Username" placeholder="Username" />
        </td>
        <td>
          <input type="text" v-model="Password" placeholder="Password" />
        </td>
        <td>
          <button
            type="button"
            v-on:click="updateManager(Name, Username, Password)"
          >
            Update
          </button>
        </td>
      </tr>
    </table>
    <p>
      <span v-if="errorUpdateAccount" style="color: red"
        >Error: {{ errorUpdateAccount }}
      </span>
    </p>
    <button type="button" v-on:click="logout()">Logout</button>
  </div>
</template>

<script src="./ManagerPageScript.js">
</script>

<style>
#table {
  width: 100%;
  border: 1px solid black;
  text-align: center;
}
#managerpage {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>