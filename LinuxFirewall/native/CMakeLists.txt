
cmake_minimum_required(VERSION 3.10)
project(nfq_bridge C)
set(CMAKE_C_STANDARD 11)
add_library(nfq_bridge SHARED nfq_bridge.c)
target_link_libraries(nfq_bridge netfilter_queue)
