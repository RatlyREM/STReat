import { useState } from 'react'
import { FaLocationArrow, FaStar } from 'react-icons/fa'
import { RiHeart3Fill, RiHeart3Line } from 'react-icons/ri'
import Image from 'next/image'
import Link from 'next/link'

export default function StoreListItem() {
  const [isLiked, setIsLiked] = useState(false)

  const toggleLike = () => {
    setIsLiked(!isLiked)
  }

  return (
    <div className="flex items-center justify-between p-4 border-b border-gray-medium">
      <Link href="/customer/stores/1" className="flex items-center w-full">
        {/* 음식점 이미지 */}
        <div className="w-16 h-16 rounded-full overflow-hidden bg-gray-dark mr-4">
          <Image
            src="/store.jpg" // 실제 이미지 경로를 넣으세요
            alt="음식점 이미지"
            width={64}
            height={64}
            className="object-cover"
          />
        </div>

        {/* 음식점 정보 */}
        <div className="flex-grow">
          <p className="text-xs text-gray-dark">#분식</p>
          <h3 className="text-lg font-semibold">조은식당</h3>
          <div className="flex items-center text-sm mt-1">
            <FaStar className="text-yellow-400 mr-1" />
            <span>4.8</span>
            <span className="mx-2">|</span>
            <FaLocationArrow className="mr-1" size={12} />
            <span>10m</span>
          </div>
        </div>
      </Link>

      <button type="button" onClick={toggleLike} className="ml-4 mb-auto">
        {isLiked ? (
          <RiHeart3Fill className="text-primary-500" size={24} />
        ) : (
          <RiHeart3Line className="text-gray-dark" size={24} />
        )}
      </button>
    </div>
  )
}