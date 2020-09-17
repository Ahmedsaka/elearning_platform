
-- Create admin_user table
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[admin_user]') AND type in (N'U'))
    BEGIN
        CREATE TABLE [dbo].[admin_user]
        (
            [id]          BIGINT IDENTITY (1,1) PRIMARY KEY NOT NULL,
            [first_name]    VARCHAR(200)                      NOT NULL,
            [last_name]     VARCHAR(200)                      NOT NULL,
            [email]         VARCHAR(200)                      NOT NULL unique,
            [password]      VARCHAR(200)                      NOT NULL,
            [date_of_birth] DATETIME,
            [create_date] DATETIME
        )
    END
GO

-- Create adminUser sp
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id (N'[dbo].[sp_create_user]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
    DROP PROCEDURE sp_create_user
GO
CREATE PROCEDURE sp_create_user( @id BIGINT=NULL OUTPUT,
                                @first_name VARCHAR(200),
                                @last_name VARCHAR(200),
                                @email VARCHAR(200),
                                @password VARCHAR(200),
                                @date_of_birth DATETIME)
AS
    SET NOCOUNT ON
INSERT INTO [dbo].[admin_user]( first_name,
                                last_name,
                                email,
                                password,
                                date_of_birth,
                                create_date)
VALUES( @first_name,
        @last_name,
        @email,
        @password,
        @date_of_birth,
        GETDATE())

SELECT @id = SCOPE_IDENTITY()
    RETURN @@Error
GO


-- sp find adminUser by email
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id (N'[dbo].[sp_find_user_by_email]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
    DROP PROCEDURE sp_find_user_by_email
GO
CREATE PROCEDURE sp_find_user_by_email(@email VARCHAR(200))
AS SET NOCOUNT ON
    BEGIN
        SELECT * FROM admin_user
        WHERE dbo.admin_user.email = @email
    END
    RETURN @@Error
GO


-- sp find adminUser by id
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id (N'[dbo].[sp_find_user_by_id]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
    DROP PROCEDURE sp_find_user_by_id
GO
CREATE PROCEDURE sp_find_user_by_id(@id BIGINT)
AS SET NOCOUNT ON
BEGIN
    SELECT * FROM admin_user
    WHERE dbo.admin_user.id = @id
END
    RETURN @@Error
GO
