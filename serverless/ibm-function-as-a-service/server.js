const express = require('express');
const application = express();
const { getPrimeNumbers } = require('./getPrimeNumbers');
application.use(express.json());
application.get('/getPrimeNumbers', async (request, response) => {
  try {
    const result = await getPrimeNumbers({n: request.query.n});
    response.status(result.statusCode || 200).set(result.headers || {}).send(result.body);
  } catch (error) {
    response.status(500).send({ error: 'Function failed', details: error.toString() });
  }
});
const port = 8080;
application.listen(port, () => console.log(`HTTP server running on http://localhost:${port}/getPrimeNumbers`));
