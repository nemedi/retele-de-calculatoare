
#include <libnetfilter_queue/libnetfilter_queue.h>
#include <linux/netfilter.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <endian.h>

static int (*managed_decide)(uint8_t*, int);

static int callback(struct nfq_q_handle *qh,
    struct nfgenmsg *nfmsg,
    struct nfq_data *nfa,
    void *data)
{
    uint32_t id = 0;
    struct nfqnl_msg_packet_hdr *ph = nfq_get_msg_packet_hdr(nfa);
    if (ph) id = be32toh(ph->packet_id); // ntohl(ph->packet_id);

    unsigned char *payload = NULL;
    int len = nfq_get_payload(nfa, &payload);

    int verdict = managed_decide(payload, len);
    return nfq_set_verdict(qh, id, verdict ? NF_ACCEPT : NF_DROP, 0, NULL);
}

void start_nfqueue(int (*decide)(uint8_t*, int))
{
    managed_decide = decide;

    struct nfq_handle *h = nfq_open();
    nfq_bind_pf(h, AF_INET);
    struct nfq_q_handle *qh = nfq_create_queue(h, 0, &callback, NULL);
    nfq_set_mode(qh, NFQNL_COPY_PACKET, 0xffff);

    int fd = nfq_fd(h);
    char buf[8192] __attribute__((aligned));
    while (recv(fd, buf, sizeof(buf), 0) >= 0)
        nfq_handle_packet(h, buf, sizeof(buf));
}
