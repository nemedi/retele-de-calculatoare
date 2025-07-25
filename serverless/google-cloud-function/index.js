exports.getPrimeNumbers = (request, response) => {
    let n = parseInt(request.query.n);
    var isNotPrime = Array(n + 1);
    var found = 0;
    for (let i = 2; i <= n - 1; i++)
        if (!isNotPrime[i]) {
            found++;
            for (let j = 2; j * i <= n; j++)
                if (j * i < isNotPrime.length) {
                    isNotPrime[j * i] = true;
                }
        }
    var numbers = Array(found);
    let k = 0;
    for (let i = 2; i <= n; i++)
        if (!isNotPrime[i])
            numbers[k++] = i;
    response.send(numbers);
};