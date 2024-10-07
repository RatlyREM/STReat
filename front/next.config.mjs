/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'streat-bucket.s3.ap-northeast-2.amazonaws.com',
        port: '',
        pathname: '/**',
      },
    ],
    domains: ['streat-bucket.s3.ap-northeast-2.amazonaws.com'],
  },
}

export default nextConfig
