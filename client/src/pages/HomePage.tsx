import { IVideo } from "../utils/types"
import { useNavigate, useParams } from "react-router-dom"
import { Box } from "@chakra-ui/react"
import { instance } from "../utils/instance"
import { useQuery } from "@tanstack/react-query"
import Video from "../components/Video"
import Search from "../components/Search"
import Loading from "../components/Loading"

const HomePage = () => {
  const {data: videos, isLoading} = useQuery<IVideo[]>({
    queryFn: getVideos,
    queryKey: ['videos']
  })
  const navigate = useNavigate()
  const {id} = useParams()
  
  async function getVideos(): Promise<IVideo[]> {
    const {data} = await instance.get<IVideo[]>(`/videos/getMany/${id}`)
    return data
  }

  if (isLoading) {
    return <Loading />
  }

  return (
    <Box className='home-page'>
      <Search />
      <Box className='home-page-videos'>
      {videos && videos.map((video) => 
        <Box 
          onClick={() => navigate('/video/' + video.id)} 
          className='video-card-wrapper'
          key={video.id}
        >
          <Video video={video} />
        </Box>
      )}
      </Box>
    </Box>
  )
}

export default HomePage