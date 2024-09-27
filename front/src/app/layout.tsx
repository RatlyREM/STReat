import type { Metadata } from 'next'
import localFont from 'next/font/local'
import './globals.css'
import Script from 'next/script'

const pretendard = localFont({
  src: '../../public/fonts/woff2/PretendardVariable.woff2',
  weight: '100 900',
  display: 'swap',
})

export const metadata: Metadata = {
  title: 'StrEAT',
  description: '주문결제를 간편하게 StrEAT!',
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html lang="ko" className={pretendard.className}>
      <body className="font-pretendard antialiased">
        <main className="min-h-screen h-full">{children}</main>
      </body>
      <Script
        type="text/javascript"
        src={`https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${process.env.NEXT_PUBLIC_NAVER_CLIENT_ID}&submodules=geocoder`}
      />
    </html>
  )
}