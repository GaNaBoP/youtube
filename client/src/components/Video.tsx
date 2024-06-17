import { Card, CardBody, Image, Text } from "@chakra-ui/react"
import { IVideo } from "../utils/types"

interface Props {
  video: IVideo
}

const Video = ({video}: Props) => {
  return (
    <Card className="video-card">
      <CardBody>
        <Image 
          className="video-card-img"
          src={video.previewUrl}
        />
        <Text className="video-card-text">
          {video.title}
        </Text>
      </CardBody>
    </Card>
  )
}

export default Video