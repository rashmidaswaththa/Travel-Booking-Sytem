using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;

namespace TravelBookingSystem.Models
{
    public class User
    {
        [BsonIgnoreExtraElements]
        public class Traveler
        {
            [BsonId]
            [BsonRepresentation(BsonType.ObjectId)]
            public string Id { get; set; }

            [BsonElement("name")]
            public string Name { get; set; } = "User Name";

            [BsonElement("email")]
            public string Email { get; set; } = "User Email";

            [BsonElement("password")]
            public string Password { get; set; } = "User Password";

            [BsonElement("role")]
            public string Role { get; set; } = "User Role";
        }
    }
}
