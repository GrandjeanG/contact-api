// Create database user for application
db.createUser({ user: 'dev', pwd: '123qweASD', roles: [ { role: 'readWrite', db: 'contact' } ] })
// Connect to database with this new user
db.auth('dev', '123qweASD')
// Create collections
db.createCollection('contacts')
db.createCollection('skills')