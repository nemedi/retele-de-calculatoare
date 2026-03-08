$TTL 1h
@   IN SOA ns.example.com. admin.example.com. (
        1   ; serial
        1h  ; refresh
        15m ; retry
        1w  ; expire
        1h  ; minimum
)
    IN NS ns.example.com.
ns  IN A 10.10.0.10
www IN A 10.10.0.20
