const express = require('express');
const application = express();
const bodyParser = require('body-parser');
const { getPrimeNumbers } = require('./getPrimeNumbers');
application.use(bodyParser.json());
application.get('/getPrimeNumbers', async (request, response) => {
  try {
    const result = await getPrimeNumbers({n: request.query.n});
    response.status(result.statusCode || 200).set(result.headers || {}).send(result.body);
  } catch (error) {
    res.status(500).send({ error: 'Function failed', details: error.toString() });
  }
});

const port = 3000;
application.listen(port, () => console.log(`HTTP server running on http://localhost:${port}/getPrimeNumbers`));
