import { Box, Button, Input } from "@chakra-ui/react"
import { useState, useEffect } from "react"
import { useQuery } from "@tanstack/react-query"
import { Link, useNavigate } from "react-router-dom"
import { instance } from "../utils/instance"
import { IVideo } from "../utils/types"

const Search = () => {
  const [search, setSearch] = useState('')
  const navigate = useNavigate()

  const {data: videos, refetch} = useQuery({
    queryKey: ['search'],
    queryFn: () => instance.post<IVideo[]>('/videos/find', {search}),
    enabled: search.length > 3,
    select: (res) => res.data
  })

  useEffect(() => {
    let timeout: ReturnType<typeof setTimeout>
    if (search.length > 3) {
      timeout = setTimeout(() => refetch(), 500)
    }
    return () => clearTimeout(timeout)
  }, [search, refetch])

  return (
    <Box className='search'>
      <Box>
        <Link to='/profile' className='material-symbols-outlined'>settings</Link>
      </Box>
      <Box className='search-input'>
        <Input 
          className='search-text-field'
          value={search}
          onChange={e => setSearch(e.target.value)} 
        />
        <Button
          className='search-button'
          colorScheme='blue'
        >Search</Button>
      </Box>
      <Box>
        <Link to='/create' className='material-symbols-outlined'>video_call</Link>
      </Box>
      <Box className='found-videos'>
        {videos && videos.map((video) => 
          <div onClick={() => navigate('/video/' + video.id)}>{video.title}</div>
        )}
      </Box>
    </Box>
  )
}

export default Search