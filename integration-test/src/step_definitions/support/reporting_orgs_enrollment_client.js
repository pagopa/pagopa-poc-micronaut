const {post, get, del} = require("./common");

function healthCheckInfo() {
    return get(`/info`)
}

function getOrganizations() {
    return get(`/organizations`)
}

function getOrganization(idOrg) {
    return get(`/organizations/${idOrg}`)
}

function createOrganization(idOrg) {
    const headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }

    return post(`/organizations/${idOrg}`, headers)
}

function removeOrganization(idOrg) {
    return del(`/organizations/${idOrg}`)
}



module.exports = {
	healthCheckInfo,
	getOrganizations,
    getOrganization,
    createOrganization,
    removeOrganization
}
