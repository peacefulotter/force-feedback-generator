'use client'
import axios from "axios";

const isLocalhost = Boolean(
    window.location.hostname === "localhost" ||
    // [::1] is the IPv6 localhost address.
    window.location.hostname === "[::1]" ||
    // 127.0.0.1/8 is considered localhost for IPv4.
    window.location.hostname.match(
      /^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/
    )
);

const SERVER_URL = isLocalhost
    ? "http://localhost:9000"
    : "https://www.example.com";

export const Axios = axios.create({
    baseURL: `${SERVER_URL}/`,
    withCredentials: true,
});

export const STREAM_URL = `${SERVER_URL}/stream`;

export const sse = new EventSource(STREAM_URL); // , { withCredentials: true }