const {post, get, del} = require("./common");

function healthCheckInfo() {
    return get(`/info`)
}

function getOrganizations() {
    return get(`/organizations`, {
        params: { frameworkType: 'micronaut' }
    })
}

function getOrganization(idOrg) {
    return get(`/organizations/${idOrg}`, {
        params: { frameworkType: 'micronaut' }
    })
}

function createOrganization(idOrg) {
    return post(`/organizations/${idOrg}`, {
        params: { frameworkType: 'micronaut' },
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
    })
}

function removeOrganization(idOrg) {
    return del(`/organizations/${idOrg}`, {
        params: { frameworkType: 'micronaut' }
    })
}



module.exports = {
	healthCheckInfo,
	getOrganizations,
    getOrganization,
    createOrganization,
    removeOrganization
}
