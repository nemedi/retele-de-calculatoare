const express = require("express");
const bodyParser = require("body-parser");
const getPrimeNumbers = require("./getPrimeNumbers");
const application = express();
application.use(bodyParser.json());
application.get("/getPrimeNumbers", async (request, response) => {
  try {
    const event = {
      httpMethod: request.method,
      queryStringParameters: request.query,
      body: request.body,
      headers: request.headers,
      path: request.path
    };
    const result = await getPrimeNumbers.handler(event, {});
    response.set(result.headers || {});
    response.status(result.statusCode || 200).send(result.body);
  } catch (error) {
    console.error("Invocation error:", error);
    response.status(500).send("Internal Server Error");
  }
});
const PORT = 3000;
application.listen(PORT, () => {
  console.log(`🚀 HTTP server running on http://localhost:${PORT}/getPrimeNumbers`);
});
