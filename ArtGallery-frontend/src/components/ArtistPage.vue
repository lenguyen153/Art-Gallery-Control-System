<template>
  <div id="artistpage">
    <h1>Welcome {{ username }}</h1>
    <h2>My Artworks</h2>
    <button type="button" v-on:click="viewArtworks()">
      <span v-if="!view.artworks"> View Artworks </span>
      <span v-else> Hide artworks </span>
    </button>
    <table v-if="view.artworks" id="table">
      <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Artstyle</th>
        <th>Number of copy</th>
        <th>Price</th>
        <th>Delete</th>
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
    <p>
      <span v-if="errorDeleteArtwork" style="color: red"
        >Error: {{ errorDeleteArtwork }}
      </span>
    </p>

    <br />

    <h2>Add Artwork</h2>
    <table id="table">
      <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Artstyle</th>
        <th>Number of copy</th>
        <th>Price</th>
        <th>Add artwork</th>
      </tr>
      <tr>
        <td>
          <input
            type="text"
            v-model="addArtworkButton.name"
            placeholder="Name"
          />
        </td>
        <td>
          <input
            type="text"
            v-model="addArtworkButton.description"
            placeholder="Description"
          />
        </td>
        <td>
          <select v-model="addArtworkButton.artstyle">
            <option>ALL</option>
            <option>ABSTRACT</option>
            <option>PHOTOREALISM</option>
            <option>IMPRESSIONISM</option>
            <option>SURREALISM</option>
            <option>POP</option>
            <option>ART_NOUVEAU</option>
            <option>MODERNISM</option>
            <option>SCULPTURE</option>
            <option>MODELING</option>
          </select>
        </td>
        <td>
          <input
            type="number"
            min="1"
            v-model="addArtworkButton.nbcopy"
            placeholder="1"
          />
        </td>
        <td>
          <input
            type="number"
            min="0"
            v-model="addArtworkButton.price"
            placeholder="Price"
          />
        </td>
        <td>
          <button
            type="button"
            v-bind:disabled="
              !(
                addArtworkButton.name &&
                addArtworkButton.description &&
                addArtworkButton.artstyle &&
                (addArtworkButton.nbcopy >= 1) &&
                (addArtworkButton.price >= 0)
              )
            "
            v-on:click="
              addArtwork(addArtworkButton.name, addArtworkButton.description, addArtworkButton.artstyle, addArtworkButton.nbcopy, addArtworkButton.price)  
            "
          >
            Add artwork
          </button>
        </td>
      </tr>
    </table>
    <p>
      <span v-if="errorAddArtwork" style="color: red"
        >Error: {{ errorAddArtwork }}
      </span>
    </p>
    <h2>Update Artwork</h2>
    <table id="table">
      <tr>
        <th>Artwork</th>
        <th>Description</th>
        <th>Artstyle</th>
        <th>Number of Copy</th>
        <th>Price</th>
        <th>Update artwork</th>
      </tr>
      <tr>
        <td>
          <select v-model="updateArtworkButton.artworkName">
            <option disabled value="">Please select one</option>
            <option v-for="artwork in artworks" :key="artwork.name">
              {{ artwork.name }}
            </option>
          </select>
        </td>
        <td>
          <input type="text" v-model="updateArtworkButton.description" placeholder="Description" />
        </td>
        <td>
          <select v-model="updateArtworkButton.artstyle">
            <option>ALL</option>
            <option>ABSTRACT</option>
            <option>PHOTOREALISM</option>
            <option>IMPRESSIONISM</option>
            <option>SURREALISM</option>
            <option>POP</option>
            <option>ART_NOUVEAU</option>
            <option>MODERNISM</option>
            <option>SCULPTURE</option>
            <option>MODELING</option>
          </select>
        </td>
        <td>
          <input type="number" min="1" v-model="updateArtworkButton.nbcopy" placeholder="nbCopy" />
        </td>
        <td>
          <input type="number" min="0" v-model="updateArtworkButton.price" placeholder="Price" />
        </td>
        <td>
          <button
            type="button"
            v-bind:disabled="
              !(
                updateArtworkButton.artworkName &&
                updateArtworkButton.description &&
                updateArtworkButton.artstyle &&
                (updateArtworkButton.nbcopy >= 1) &&
                (updateArtworkButton.price >= 0)
              )
            "
            v-on:click="
              updateArtwork(updateArtworkButton.artworkName, updateArtworkButton.description, updateArtworkButton.artstyle, updateArtworkButton.nbcopy, updateArtworkButton.price)
            "
          >
            Update artwork
          </button>
        </td>
      </tr>
    </table>
    <p>
      <span v-if="errorUpdateArtwork" style="color: red"
        >Error: {{ errorUpdateArtwork }}
      </span>
    </p>
    <h2>My Transactions</h2>
    <button type="button" v-on:click="viewTransactions()">
      <span v-if="!view.transactions"> View Transactions </span>
      <span v-else> Hide transactions </span>
    </button>

    <br /><br />

    <table v-if="view.transactions" id="table">
      <tr>
        <th>Price</th>
        <th>Commission</th>
        <th>Tax</th>
        <th>Date</th>
      </tr>
      <tr v-for="transaction in transactions" :key="transaction">
        <td>{{ transaction.price }}</td>
        <td>{{ transaction.commissionApplied }}</td>
        <td>{{ transaction.taxApplied }}</td>
        <td>{{ transaction.date }}</td>
      </tr>
    </table>

    <br />

    <h2>Update Account</h2>
    <table id="table">
      <tr>
        <th>Name</th>
        <td>
          <input type="text" v-model="updateAccountButtons.name" placeholder="John Doe" />
        </td>
        <td>
          <button
            type="button"
            v-bind:disabled="!updateAccountButtons.name"
            v-on:click="changeName(updateAccountButtons.name)"
          >
            Change
          </button>
        </td>
      </tr>
      <tr>
        <th>Password</th>
        <td>
          <input type="text" v-model="updateAccountButtons.pswd" placeholder="password1234" />
        </td>
        <td>
          <button
            type="button"
            v-bind:disabled="!updateAccountButtons.pswd"
            v-on:click="changePassword(updateAccountButtons.pswd)"
          >
            Change
          </button>
        </td>
      </tr>
      <tr>
        <th>Ship to gallery</th>
        <td>
          <select v-model="updateAccountButtons.shipToGallery">
            <option disabled value="">Please select one</option>
            <option>true</option>
            <option>false</option>
          </select>
        </td>
        <td>
          <button
            type="button"
            v-bind:disabled="!updateAccountButtons.shipToGallery"
            v-on:click="changeShipping(updateAccountButtons.shipToGallery)"
          >
            Change
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

<script src="./ArtistPageScript.js">
</script>

<style>
#table {
  width: 100%;
  border: 1px solid black;
  text-align: center;
}
#artistpage {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>